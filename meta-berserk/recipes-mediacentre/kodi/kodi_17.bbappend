require kodi-version.inc

# убираю не работающий патч (был для версии 17.3)
SRC_URI_remove = "file://0013-FTPParse.cpp-use-std-string.patch"

# отключаю патч, так как systemd не использую
# disable, because systemd is not used
SRC_URI_remove = "file://0004-handle-SIGTERM.patch"

# добавляю патч обратного портирования для RPI взятого из libreelec
SRC_URI_append += "file://kodi-krypton-rpb-backports.patch"

# исправление error adding symbols: DSO missing from command line
SRC_URI_append += "file://vchostif.patch"


MENU_ICON = "addons/skin.estuary/media/icons/settings"
# добавление нового пункта в меню настроек (значок шестеренки)
SRC_URI_append += "file://bs-menu.patch file://icon/bs-network.png"
do_configure_prepend() {
    install -m 0644 ${WORKDIR}/icon/bs-network.png ${S}/${MENU_ICON}
}

# дополнительные зависимости для kodi plugins
RRECOMMENDS_${PN}_append = "python-xml python-misc python-db \
                            python-crypt python-threading python-math python-email \
                            python-io python-netserver python-urllib3 python-datetime"


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

# опция для появления всплывающего сообщения в Kodi о подключении внешнего носителя
# например USB или microSDHC диска
EXTRA_OECONF_append = " --enable-optical-drive"
