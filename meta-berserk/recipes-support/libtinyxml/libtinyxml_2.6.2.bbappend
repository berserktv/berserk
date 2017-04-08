# дополнительный параметр настройки tinyxml через pkgconfig
FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://tinyxml.pc"

do_install_append() {
    install -d ${D}${libdir}/pkgconfig
    install -m 0644 ${WORKDIR}/tinyxml.pc ${D}${libdir}/pkgconfig/tinyxml.pc
}

