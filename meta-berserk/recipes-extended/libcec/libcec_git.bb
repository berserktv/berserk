SUMMARY = "USB CEC Adaptor communication Library"
HOMEPAGE = "http://libcec.pulse-eight.com/"

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=e61fd86f9c947b430126181da2c6c715"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INHIBIT_PACKAGE_STRIP = "1"

DEPENDS = "p8platform udev lockdev ncurses swig-native python3"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'libx11 libxrandr', '', d)}"
DEPENDS_append_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', ' userland', d)}"

PV = "3.1.0+gitr${SRCPV}"

SRCREV = "bb09230246469b36c3a87b911aec32df31538d43"
SRC_URI = "git://github.com/Pulse-Eight/libcec.git \
           file://python-install-location.patch"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

# Create the wrapper for python3
PACKAGES += "python3-${BPN}"
FILES_python3-${BPN} = "${libdir}/python3*"


# cec-client and xbmc need the .so present to work :(
FILES_${PN} += "${libdir}/*.so"
INSANE_SKIP_${PN} = "dev-so"
# Adapter shows up as a CDC-ACM device
RRECOMMENDS_${PN} = "kernel-module-cdc-acm"