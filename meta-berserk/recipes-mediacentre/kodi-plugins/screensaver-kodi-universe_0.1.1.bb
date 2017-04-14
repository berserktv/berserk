# Very simple Screensaver for Kodi mediacenter, autor Alexander Demachev (https://berserk.tv)

DESCRIPTION = "Screensaver Kodi Universe"
SECTION = "configs"
PR = "r1"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

KODI_HOME_DIR = "/home/root/.kodi"
KODI_ADDON_NAME = "screensaver.kodi.universe"

SRC_URI = "git://github.com/berserktv/${KODI_ADDON_NAME}.git;branch=master"
SRCREV = "d32bcb5101f179c1b2a1db833936832519468ea1"


S = "${WORKDIR}/git"
PACKAGES = "${PN}"
FILES_${PN} = "${KODI_HOME_DIR}"

do_install_append() {
    install -d ${D}/${KODI_HOME_DIR}/addons/${KODI_ADDON_NAME}
    rm -fr ${S}/.git
    cp -vfR ${S}/* ${D}/${KODI_HOME_DIR}/addons/${KODI_ADDON_NAME}
}
