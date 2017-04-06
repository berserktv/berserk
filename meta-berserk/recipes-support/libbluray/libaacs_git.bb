SUMMARY = "Open implementation of the AACS specification"
SECTION = "libs/multimedia"
LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=4b54a1fd55a448865a0b32d41598759d"


SRCREV = "0fffa67066f16e22967e06c92fb63c79b5a9a5fe"
PV = "0.8.1"

#SRCREV = "042cf8e6f21ac560edc1c4de33ea805077f415e6"
#PV = "0.8.0"
#SRCREV = "81599b23fbe2fd37317d5a7a5642fa7dd9cf7bb4"
#PV = "0.7.0"

SRC_URI = "git://git.videolan.org/${PN}.git;protocol=git \
           "
S = "${WORKDIR}/git"

inherit autotools-brokensep lib_package pkgconfig

DEPENDS += "libgcrypt"

###EXTRA_OECONF += "--with-libgcrypt-prefix=${STAGING_DIR_HOST}${prefix}"
