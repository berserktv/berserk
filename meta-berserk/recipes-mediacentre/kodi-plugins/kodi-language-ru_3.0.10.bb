DESCRIPTION = "resource.language.ru_ru for KODI Media Center"
# как альтернативный вариант можно взять русскую локализацию
# из официального репозитория "https://github.com/xbmc/translations.git"
# но проект весит 1.2 Гб вместе с базой git, что не очень быстро загружается
# поэтому локализацию пока добавил в качестве архива

SECTION = "configs"
PR = "r0"

LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

require ../kodi/kodi-dir.inc
LANG_PACKAGE = "resource.language.ru_ru"

SRC_URI = "file://${LANG_PACKAGE}.tar.gz"

S = "${WORKDIR}"

PACKAGES = "${PN}"

FILES_${PN} +=  "${KODI_ADDON_DIR}"


do_install() {
    # Kodi addons
    install -d "${D}${KODI_ADDON_DIR}/${LANG_PACKAGE}"
    install -d "${D}${KODI_ADDON_DIR}/${LANG_PACKAGE}/resources"

    install -m 0644 ${S}/${LANG_PACKAGE}/icon.png ${D}${KODI_ADDON_DIR}/${LANG_PACKAGE}
    install -m 0644 ${S}/${LANG_PACKAGE}/addon.xml ${D}${KODI_ADDON_DIR}/${LANG_PACKAGE}
    install -m 0755 ${S}/${LANG_PACKAGE}/resources/* ${D}${KODI_ADDON_DIR}/${LANG_PACKAGE}/resources
    chown root:root -R ${D}
}














