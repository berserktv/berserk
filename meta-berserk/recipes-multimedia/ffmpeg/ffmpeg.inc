SUMMARY = "A complete, cross-platform solution to record, convert and stream audio and video"
HOMEPAGE = "http://ffmpeg.org/"
SECTION = "libs"
LICENSE = "GPLv2+"
LICENSE_FLAGS = "commercial"

ARM_INSTRUCTION_SET = "arm"

DEPENDS = "zlib libogg libvorbis libtheora yasm-native"
RCONFLICTS_ffmpeg = "libav"

INC_PR = "r8"

inherit autotools pkgconfig

FULL_OPTIMIZATION_armv7a = "-fexpensive-optimizations -fomit-frame-pointer -O4 -ffast-math"
BUILD_OPTIMIZATION = "${FULL_OPTIMIZATION}"

EXTRA_FFCONF_armv7a = "--cpu=cortex-a8"
EXTRA_FFCONF ?= ""

PACKAGECONFIG ??= "bzip2 x264 ${@base_contains('DISTRO_FEATURES', 'x11', 'x11', '', d)}"
PACKAGECONFIG[jack] = "--enable-indev=jack,--disable-indev=jack,jack"
PACKAGECONFIG[bzip2] = "--enable-bzlib,--disable-bzlib,bzip2"
PACKAGECONFIG[schroedinger] = "--enable-libschroedinger,--disable-libschroedinger,schroedinger"
PACKAGECONFIG[gsm] = "--enable-libgsm,--disable-libgsm,libgsm"
PACKAGECONFIG[x264] = "--enable-libx264,--disable-libx264,x264"
PACKAGECONFIG[vpx] = "--enable-libvpx,--disable-libvpx,libvpx"
PACKAGECONFIG[mp3lame] = "--enable-libmp3lame,--disable-libmp3lame,lame"
PACKAGECONFIG[faac] = "--enable-libfaac,--disable-libfaac,faac"
PACKAGECONFIG[x11] = "--enable-x11grab,--disable-x11grab,virtual/libx11 libxfixes libxext xproto virtual/libsdl"

EXTRA_OECONF = " \
    --disable-stripping \
    --disable-debug \
    --enable-shared \
    --enable-pthreads \
    --enable-gpl \
    --enable-avfilter \
    \
    --cross-prefix=${TARGET_PREFIX} \
    --prefix=${prefix} \
    \
    --enable-ffserver \
    --enable-ffplay \
    --ld="${CCLD}" \
    --enable-libtheora  \
    --enable-libvorbis \
    --arch=${TARGET_ARCH} \
    --target-os="linux" \
    --enable-cross-compile \
    --extra-cflags="${TARGET_CFLAGS} ${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS}" \
    --extra-ldflags="${TARGET_LDFLAGS}" \
    --sysroot="${STAGING_DIR_TARGET}" \
    --enable-hardcoded-tables \
    ${EXTRA_FFCONF} \
    --libdir=${libdir} \
    --shlibdir=${libdir} \
"

# remove the "--build=x86_64-linux" option automatically passed to configure
do_configure() {
    ${S}/configure ${EXTRA_OECONF}
}


FFMPEG_LIBS = "libavcodec libavdevice libavformat \
               libavutil libpostproc libswscale libavfilter libswresample"

FILES_${PN} = "${bindir} ${datadir}/ffprobe.xsd"
FILES_${PN}-dev = "${includedir}/${PN}"
FILES_${PN}-dev = "${includedir}"

LEAD_SONAME = "libavcodec.so"

PACKAGES += "ffmpeg-x264-presets \
             libavcodec  libavcodec-dev  libavcodec-dbg \
             libavdevice libavdevice-dev libavdevice-dbg \
             libavformat libavformat-dev libavformat-dbg \
             libavutil   libavutil-dev   libavutil-dbg \
             libpostproc libpostproc-dev libpostproc-dbg \
             libswscale  libswscale-dev  libswscale-dbg \
             libavfilter libavfilter-dev libavfilter-dbg \
             libavcore   libavcore-dev   libavcore-dbg \
             libswresample libswresample-dev libswresample-dbg \
             ffmpeg-examples \
            "

FILES_ffmpeg-x264-presets = "${datadir}/*.ffpreset"

FILES_${PN}-dev = "${includedir}"
FILES_libavcodec = "${libdir}/libavcodec*.so.*"
FILES_libavcodec-dev = "${libdir}/libavcodec*.so ${libdir}/pkgconfig/libavcodec.pc ${libdir}/libavcodec*.a"
FILES_libavcodec-dbg += "${libdir}/.debug/libavcodec*"

FILES_libavdevice = "${libdir}/libavdevice*.so.*"
FILES_libavdevice-dev = "${libdir}/libavdevice*.so ${libdir}/pkgconfig/libavdevice.pc ${libdir}/libavdevice*.a"
FILES_libavdevice-dbg += "${libdir}/.debug/libavdevice*"

FILES_libavformat = "${libdir}/libavformat*.so.*"
FILES_libavformat-dev = "${libdir}/libavformat*.so ${libdir}/pkgconfig/libavformat.pc ${libdir}/libavformat*.a"
FILES_libavformat-dbg += "${libdir}/.debug/libavformat*"

FILES_libavutil = "${libdir}/libavutil*.so.*"
FILES_libavutil-dev = "${libdir}/libavutil*.so ${libdir}/pkgconfig/libavutil.pc ${libdir}/libavutil*.a"
FILES_libavutil-dbg += "${libdir}/.debug/libavutil*"

FILES_libpostproc = "${libdir}/libpostproc*.so.*"
FILES_libpostproc-dev = "${libdir}/libpostproc*.so  ${libdir}/pkgconfig/libpostproc.pc ${libdir}/libpostproc*.a ${includedir}/postproc"
FILES_libpostproc-dbg += "${libdir}/.debug/libpostproc*"

FILES_libswscale = "${libdir}/libswscale*.so.*"
FILES_libswscale-dev = "${libdir}/libswscale*.so ${libdir}/pkgconfig/libswscale.pc ${libdir}/libswscale*.a"
FILES_libswscale-dbg += "${libdir}/.debug/libswscale*"

FILES_libavfilter = "${libdir}/libavfilter*.so.*"
FILES_libavfilter-dev = "${libdir}/libavfilter*.so ${libdir}/pkgconfig/libavfilter.pc ${libdir}/libavfilter*.a"
FILES_libavfilter-dbg += "${libdir}/.debug/libavfilter*"

FILES_libavcore = "${libdir}/libavcore*.so.*"
FILES_libavcore-dev = "${libdir}/libavcore*.so ${libdir}/pkgconfig/libavcore.pc ${libdir}/libavcore*.a"
FILES_libavcore-dbg += "${libdir}/.debug/libavcore*"


FILES_libswresample = "${libdir}/libswresample*.so.*"
FILES_libswresample-dev = "${libdir}/libswresample*.so ${libdir}/pkgconfig/libswresample.pc ${libdir}/libswresample*.a"
FILES_libswresample-dbg += "${libdir}/.debug/libswresample*"


FILES_ffmpeg-examples = "${datadir}/examples/*"
