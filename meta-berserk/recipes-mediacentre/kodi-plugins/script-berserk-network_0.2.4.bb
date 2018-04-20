# Plugin for Kodi mediacenter
# "Network Manager" is a network management software for Ethernet and Wifi network connections
# Project "Berserk" - build Kodi for the Raspberry Pi platform, autor Alexander Demachev, site https://berserk.tv
# license -  The MIT License (MIT)

DESCRIPTION = "Network Manager is a network management software for Ethernet and Wifi network connections"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"


SRC_URI = "git://github.com/berserktv/script.berserk.network.git;branch=master"
SRCREV = "c05defdfc5d72b264777fba53480f42f348b0196"

require ../kodi/kodi-dir.inc
KODI_ADDON_NAME = "script.berserk.network"
KODI_ADDON_PATH = "${KODI_ADDON_DIR}/${KODI_ADDON_NAME}"


S = "${WORKDIR}/git"

PACKAGES = "${PN}"
FILES_${PN} = "${KODI_HOME_DIR}"

RDEPENDS_${PN} += "python-subprocess python-fcntl"

do_install_append() {
    install -d ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${S}/addon.xml     ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${S}/changelog.txt ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${S}/default.py    ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${S}/icon.png      ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${S}/service.py    ${D}/${KODI_ADDON_PATH}

    cp -vfR ${S}/resources ${D}/${KODI_ADDON_PATH}
}
