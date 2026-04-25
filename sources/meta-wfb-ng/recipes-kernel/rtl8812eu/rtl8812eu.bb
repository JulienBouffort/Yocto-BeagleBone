SUMMARY = "Realtek RTL8812EU USB WiFi driver patche pour WFB-NG"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://Makefile;md5=abacbe004dc3f483c8425d36a287d010"

inherit module

SRC_URI = "git://github.com/svpcom/rtl8812eu.git;protocol=https;branch=v5.15.0.1"
SRCREV = "fc10f90c66e9569b2cc3e4a7319006b037b27792"

PV = "5.15.0.1+git${SRCPV}"
S = "${WORKDIR}/git"

DEPENDS = "virtual/kernel"
RDEPENDS:${PN} = "kernel-module-cfg80211"

EXTRA_OEMAKE = "KSRC=${STAGING_KERNEL_DIR} CONFIG_PLATFORM_I386_PC=n CONFIG_PLATFORM_ARM_RPI=y"

KERNEL_MODULE_AUTOLOAD += "8812eu"

FILES:${PN} += "${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra/8812eu.ko"
