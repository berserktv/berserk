FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"

SRCREV = "a9a7a20071bfd759e72e7053cee92e6f5cfb5e48"
PV = "17.6+gitr${SRCPV}"

# убираю не работающий патч (был для версии 17.3)
SRC_URI_remove = "file://0013-FTPParse.cpp-use-std-string.patch"

# отключаю патч, так как systemd не использую
# disable, because systemd is not used
SRC_URI_remove = "file://0004-handle-SIGTERM.patch"


# специфическии опции для плат Raspberry Pi
# реализация OPENGL обязательно должна быть --enable-gles
# для kodi зависимости сборки указаны в docs/README.linux => libxmu libxinerama libxtst xdpyinfo
# для сборки этих библиотек в DISTRO_FEATURES необходима зависимость "x11"
# но сам kodi для RPI1 и RPI2,3 собирается с опцией --disable-x11
###EXTRA_OECONF_append_raspberrypi  = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi --disable-x11"
# конфигурация для плат RPI2 и RPI3
###EXTRA_OECONF_append_raspberrypi2 = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi2 --disable-x11"
BS_RPI = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi --disable-x11"
BS_RPI3 = " --disable-gl --enable-openmax --enable-player=omxplayer --with-platform=raspberry-pi2 --disable-x11"

EXTRA_OECONF_append = "${@bb.utils.contains('MACHINE', 'raspberrypi', '${BS_RPI}', '', d)}"
EXTRA_OECONF_append = "${@bb.utils.contains('MACHINE', 'raspberrypi2', '${BS_RPI3}', '', d)}"
EXTRA_OECONF_append = "${@bb.utils.contains('MACHINE', 'raspberrypi3', '${BS_RPI3}', '', d)}"
