SUMMARY = "WFB-NG - Wifibroadcast Next Generation"
DESCRIPTION = "Liaison video longue portee pour drones FPV via WiFi monitor mode"
HOMEPAGE = "https://github.com/svpcom/wfb-ng"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=84dcc94da3adb52b53ae4fa38fe49e5d"

DEPENDS = " \
    libpcap \
    openssl \
    libsodium \
    python3 \
    python3-twisted \
    python3-pyroute2 \
"

RDEPENDS:${PN} = " \
    libpcap \
    openssl \
    libsodium \
    python3 \
    python3-twisted \
    python3-pyroute2 \
    kernel-module-8812eu \
"

SRC_URI = "git://github.com/svpcom/wfb-ng.git;protocol=https;branch=master"
SRCREV = "a69d0ed519aaf520cd802218857544e4a08accbb"

PV = "1.0+git${SRCPV}"
S = "${WORKDIR}/git"

inherit pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "wifibroadcast.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

export OS_CODENAME = "bookworm"
export COMMIT = "${SRCREV}"
export SOURCE_DATE_EPOCH = "0"
export VERSION = "24.04"

do_compile() {
    oe_runmake all \
        CC="${CC}" \
        CXX="${CXX}" \
        PKG_CONFIG="${PKG_CONFIG}" \
        PREFIX="/usr"
}

do_install() {

    # Installer les binaires manuellement
    install -d ${D}${bindir}
    install -m 0755 ${S}/wfb_tx ${D}${bindir}/wfb_tx
    install -m 0755 ${S}/wfb_rx ${D}${bindir}/wfb_rx
    install -m 0755 ${S}/wfb_tun ${D}${bindir}/wfb_tun
    install -m 0755 ${S}/wfb_keygen ${D}${bindir}/wfb_keygen

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/scripts/wifibroadcast.service \
        ${D}${systemd_system_unitdir}/wifibroadcast.service

    install -d ${D}${sysconfdir}/wifibroadcast
    install -m 0644 ${S}/scripts/bind \
        ${D}${sysconfdir}/wifibroadcast/ || true


}

FILES:${PN} += " \
    ${bindir}/wfb_tx \
    ${bindir}/wfb_rx \
    ${bindir}/wfb_tun \
    ${sysconfdir}/wifibroadcast/ \
    ${systemd_system_unitdir}/wifibroadcast.service \
"
