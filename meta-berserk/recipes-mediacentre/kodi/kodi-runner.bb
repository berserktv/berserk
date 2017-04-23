DESCRIPTION = "runner for KODI Media Center"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

FILESEXTRAPATHS_prepend := "${THISDIR}/kodi:"

# секция установки скрипта запуска
SRC_URI = "file://run/kodi.init file://run/restarter.sh"

inherit update-rc.d

S = "${WORKDIR}/run"


INITSCRIPT_NAME = "kodi"
INITSCRIPT_PARAMS = "start 01 2 3 4 5 . stop 90 0 1 6 ."


PACKAGES = "${PN}"
FILES_${PN} = "/etc/init.d"

do_install_append() {
    # скрипт запуска
    install -d ${D}/etc/init.d
    install -m 0755 ${S}/kodi.init ${D}/etc/init.d/kodi
    install -m 0755 ${S}/restarter.sh ${D}/etc/init.d/restarter.sh
}

        
       
    
  
