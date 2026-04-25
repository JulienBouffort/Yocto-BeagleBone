FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8

RUN apt-get update && apt-get install -y \
    gawk wget git diffstat unzip texinfo \
    gcc build-essential chrpath socat cpio \
    python3 python3-pip python3-pexpect \
    python3-git python3-jinja2 python3-subunit \
    xz-utils zstd liblz4-tool lz4 bzip2 gzip tar \
    debianutils iputils-ping file locales \
    libacl1 libegl1-mesa libsdl1.2-dev mesa-common-dev \
    curl rsync \
    bc libssl-dev libelf-dev \
    pkg-config \
    && locale-gen en_US.UTF-8 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN useradd -m -s /bin/bash yocto && \
    echo "yocto ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

USER yocto
WORKDIR /home/yocto

CMD ["/bin/bash"]
