SUMMARY = "decoding library for DTS Coherent Acoustics streams"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

SRC_URI = "http://download.videolan.org/pub/videolan/libdca/${PV}/libdca-${PV}.tar.bz2"

SRC_URI[md5sum] = "dab6b2795c66a82a6fcd4f8343343021"
SRC_URI[sha256sum] = "dba022e022109a5bacbe122d50917769ff27b64a7bba104bd38ced8de8510642"


S = "${WORKDIR}/${PN}-${PV}"

inherit autotools lib_package pkgconfig

do_configure_prepend() {
        QUILT_PATCHES="debian/patches quilt push -a"
        # single precision is enough and speeds up libdca by about 10-15%
        sed -i -e "s/double/sample_t/g" ${S}/libdca/*.c ${S}/libdca/*.h
}


