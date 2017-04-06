DESCRIPTION = "TV config for Berserk Image"
SECTION = "configs"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

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
        

        
       
    
