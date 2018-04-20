# Very simple Screensaver for Kodi mediacenter, autor recipe Alexander Demachev (https://berserk.tv)

DESCRIPTION = "Plugin Video Youtube"
SECTION = "configs"
PR = "r1"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

FILESEXTRAPATHS_prepend := "${THISDIR}/addons:"

KODI_HOME_DIR = "/home/root/.kodi"
KODI_ADDON_NAME = "plugin.video.youtube"

SRC_URI = "file://${KODI_ADDON_NAME}.tar.gz"

S = "${WORKDIR}"
PACKAGES = "${PN}"
FILES_${PN} = "${KODI_HOME_DIR}"

do_install_append() {
    install -d ${D}/${KODI_HOME_DIR}/addons/${KODI_ADDON_NAME}
    cp -vfR ${S}/${KODI_ADDON_NAME} ${D}/${KODI_HOME_DIR}/addons
}

        
       
    
  