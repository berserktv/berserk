SUMMARY = "KODI Media Center"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.GPL;md5=930e2a5f63425d8dd72dbd7391c43c46"

# официальная позиция коdi, испльзование только ffmpeg
RCONFLICTS_${PN} = "libav"
# используется userland
RCONFLICTS_${PN} = "mesa"

require kodi-version.inc


DEPENDS = "libusb1 libcec libplist expat yajl gperf-native \
           fribidi mpeg2dec samba fontconfig curl python libass libmodplug \
           libmicrohttpd wavpack libmms cmake-native \
           virtual/egl mysql5 sqlite3 libmms faad2 libcdio libpcre boost lzo enca \
           avahi libsamplerate0 bzip2 \
           libxmu libxinerama libxtst xdpyinfo \
           jasper zip-native zlib libtinyxml libmad \
           libbluray libnfs swig-native libvorbis tiff \
           libxslt taglib libssh rtmpdump shairplay ffmpeg \
           libsquish x264 libtheora git-replacement-native \
           jsonschemabuilder-native \
           "



RDEPENDS_${PN} += "python-json"

inherit autotools-brokensep gettext python-dir


S = "${WORKDIR}/git"

# breaks compilation
CCACHE = ""

CACHED_CONFIGUREVARS += " \
    ac_cv_path_PYTHON="${STAGING_BINDIR_NATIVE}/python-native/python" \
"

PACKAGECONFIG ??= "${@base_contains('DISTRO_FEATURES', 'opengl', 'opengl', 'openglesv2', d)}"
PACKAGECONFIG[opengl] = "--enable-gl,--disable-gl,glew"
PACKAGECONFIG[openglesv2] = "--enable-gles,,userland"
### example used bb.utils.contains
### EXTRA_OECONF += ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', '--enable-gl', '--enable-gles', d)} 


EXTRA_OECONF = " \
  --disable-static \
  --enable-shared \
  gl_cv_func_gettimeofday_clobber=no \
  ac_cv_lib_bluetooth_hci_devid=no \
  --disable-debug \
  --disable-optimizations \
  --disable-vdpau \
  --disable-vaapi \
  --disable-vtbdecoder \
  --disable-tegra \
  --disable-profiling \
  --enable-joystick=no \
  --enable-libcec \
  --enable-udev \
  --disable-libusb \
  --disable-goom \
  --disable-rsxs \
  --disable-projectm \
  --disable-waveform \
  --disable-spectrum \
  --disable-fishbmc \
  --disable-ccache \
  --enable-alsa \
  --disable-pulse \
  --enable-rtmp \
  --enable-samba \
  --enable-nfs \
  --disable-libcap \
  --enable-dvdcss \
  --disable-mid \
  --enable-avahi \
  --enable-upnp \
  --enable-mysql \
  --enable-ssh \
  --enable-airplay \
  --enable-airtunes \
  --enable-non-free \
  --disable-asap-codec \
  --enable-webserver \
  --enable-optical-drive \
  --enable-libbluray \
  --disable-texturepacker \
  --with-ffmpeg=shared \
"

# специфическии опции для плат Raspberry Pi
# реализация OPENGL обязательно должна быть --enable-gles см. строку выше bb.utils.contains(DISTRO_FEATURES)
# для kodi зависимости сборки указаны в docs/README.linux => libxmu libxinerama libxtst xdpyinfo
# для сборки этих библиотек в DISTRO_FEATURES необходима зависимость "x11"
# но сам kodi для RPI1 и RPI2,3 собирается с опцией --disable-x11
EXTRA_OECONF_append_raspberrypi  = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi --disable-x11"
# конфигурация для плат RPI2 и RPI3
EXTRA_OECONF_append_raspberrypi2 = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi2 --disable-x11"


FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

# for python modules
export HOST_SYS
export BUILD_SYS
export STAGING_LIBDIR
export STAGING_INCDIR
export PYTHON_DIR

do_configure() {
    sh bootstrap
    oe_runconf
}


do_compile_prepend() {
    for i in $(find . -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' $i
    done

    for i in $(find . -name "*.mak*" -o    -name "Makefile") ; do
        sed -i -e 's:I/usr/include:I${STAGING_INCDIR}:g' -e 's:-rpath \$(libdir):-rpath ${libdir}:g' $i
    done
}

INSANE_SKIP_${PN} = "rpaths"

do_install_append() {
    # исправляю путь в cmake файле ${STAGING_DIR_TARGET}/usr/lib/kodi/kodi-config.cmake
    # иначе при сборке addons не будет найден addon-helpers (как сделать более правильно ???)
    sed -i "s| /usr/lib/kodi| ${STAGING_DIR_TARGET}/usr/lib/kodi|g" ${D}/usr/lib/kodi/kodi-config.cmake
    sed -i "s| /usr/include/kodi| ${STAGING_DIR_TARGET}/usr/include/kodi|g" ${D}/usr/lib/kodi/kodi-config.cmake
}


PACKAGES = " \
        ${PN}-dbg \
        ${PN}-dev \
        ${PN}-doc \
        ${PN} \
        "

PROVIDES = "${PACKAGES}"

FILES_${PN}-doc = "${docdir}"
FILES_${PN}-dev = "${includedir}"

FILES_${PN}-dbg  = "${libdir}/kodi/.debug \
                    ${libdir}/kodi/*/.debug \ 
                    ${libdir}/kodi/*/*/.debug \
                    ${libdir}/kodi/*/*/*/.debug \
                    ${libdir}/kodi/addons/library.xbmc.addon/.debug \
                    ${prefix}/src/debug \
                    "

FILES_${PN} = "${bindir} \
               ${libdir}/kodi \
               ${libdir}/xbmc \
               ${datadir}/xbmc \
               ${datadir}/icons \
               ${datadir}/xsessions \
               ${datadir}/applications \
               ${datadir}/kodi/media \
               ${datadir}/kodi/system \
               ${datadir}/kodi/addons \
               ${datadir}/kodi/userdata \
               "

RRECOMMENDS_${PN}_append = " libcec \
                             python \
                             python-lang \
                             python-re \
                             python-netclient \
                             libcurl \
                             xdpyinfo "

RRECOMMENDS_${PN}_append_libc-glibc = " glibc-charmap-ibm850 glibc-gconv-ibm850"
