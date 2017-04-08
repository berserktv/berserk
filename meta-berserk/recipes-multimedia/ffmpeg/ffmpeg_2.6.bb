require ffmpeg.inc

LIC_FILES_CHKSUM = "file://COPYING.GPLv2;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.GPLv3;md5=d32239bcb673463ab874e80d47fae504 \
                    file://COPYING.LGPLv2.1;md5=bd7a443320af8c812e4c18d1b79df004 \
                    file://COPYING.LGPLv3;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI = "git://source.ffmpeg.org/ffmpeg.git;branch=${SRCBRANCH} \
           file://0001-mpeg4video-Signal-unsupported-GMC-with-more-than-one.patch \
          "

SRCBRANCH = "release/2.6"
SRCREV = "${AUTOREV}"
S = "${WORKDIR}/git"


DEFAULT_PREFERENCE = "-1"

