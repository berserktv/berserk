# Very simple Screensaver for Kodi mediacenter, autor Alexander Demachev (https://berserk.tv)

DESCRIPTION = "Screensaver Kodi Universe"
SECTION = "configs"
PR = "r1"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

GIT_REPO_DIR = "github.com/berserktv"

require ../kodi/kodi-dir.inc
KODI_ADDON_NAME = "screensaver.kodi.universe"
KODI_ADDON_PATH = "${KODI_ADDON_DIR}/${KODI_ADDON_NAME}"
KODI_MEDIA_PATH = "${KODI_ADDON_PATH}/resources/skins/default/media/kodi-universe.mkv"
KODI_ADDON_VIDEO = "https://${GIT_REPO_DIR}/bs-res/raw/master/kodi-plugins/${KODI_ADDON_NAME}/isengard/kodi-universe.mkv"

SRC_URI = "${KODI_ADDON_VIDEO};protocol=https;name=kodi-video \
           git://${GIT_REPO_DIR}/${KODI_ADDON_NAME}.git;branch=master;name=kodi-addon \
           "

SRCREV_kodi-addon = "a15fd087f9d1bb67388815d18fde1c1a59919290"
SRC_URI[kodi-video.md5sum] = "98f941a6ad29fe7ad4735e8a0eedd4dc"
SRC_URI[kodi-video.sha256sum] = "45e6a93eee87b8506d351d13214ffbfe005526b6a146a2d0086e88050aa44785"


S = "${WORKDIR}/git"
PACKAGES = "${PN}"
FILES_${PN} = "${KODI_HOME_DIR}"

do_install_append() {
    install -d ${D}/${KODI_ADDON_PATH}
    rm -fr ${S}/.git
    cp -vfR ${S}/* ${D}/${KODI_ADDON_PATH}
    install -m 0644 ${WORKDIR}/kodi-universe.mkv ${D}/${KODI_MEDIA_PATH}
}
