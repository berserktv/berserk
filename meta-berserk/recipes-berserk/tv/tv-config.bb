DESCRIPTION = "TV config for Berserk Image"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

require tv-dir.inc

S = "${WORKDIR}"

SRC_URI += " \
    file://${TV_CONFIG} \
    "

PACKAGES = "${PN}"

FILES_${PN} +=  "${TV_CONFIG_DIR} \
                "

do_install() {
    install -d "${D}${TV_CONFIG_DIR}"
    install -m 0644 "${S}/${TV_CONFIG}" "${D}${TV_CONFIG_DIR}/${TV_CONFIG}"
}
        

        
       
    
