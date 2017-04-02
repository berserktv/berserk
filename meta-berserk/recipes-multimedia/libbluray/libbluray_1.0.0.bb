require libbluray.inc

DEPENDS += "libaacs libdca libdvdcss"
RDEPENDS_${PN} += "libaacs libdca libdvdcss"

SRCREV="491c7e8501d7e7ea853700a2f35a18f11ad40a5b"
LIC_FILES_CHKSUM="file://COPYING;md5=435ed639f84d4585d93824e7da3d85da"
PR = "${INC_PR}.2"

#SRC_URI[archive.md5sum] = "b009e0550c3cf54d740e92a5f47821e1"
#SRC_URI[archive.sha256sum] = "61602402d846a50e8ddf82c3fb5b81984de95ed56abfe0f1e22426d1901c564f"

do_package_qa() {
}

