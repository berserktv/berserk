FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://dropbear"

INITSCRIPT_NAME = "dropbear"
INITSCRIPT_PARAMS = "defaults 99"


do_install_append() {
    install -d ${D}${sysconfdir}/default
    install -m 0644  ${WORKDIR}/dropbear ${D}${sysconfdir}/default/dropbear
}
