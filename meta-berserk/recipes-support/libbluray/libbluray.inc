DESCRIPTION = "libbluray for oe."
SECTION = "misc"
HOMEPAGE = "http://videolan.org"
DEPENDS = ""
LICENSE = "GPLv2"
INC_PR = "r5"
S="${WORKDIR}/git"
SRC_URI = "git://git.videolan.org/libbluray.git;branch=master;protocol=git \
		"
inherit autotools-brokensep

FILES_${PN} = "/"
