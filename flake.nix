{
  description = "Minecraft mod development shell";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-unstable";
    flake-parts.url = "github:hercules-ci/flake-parts";
  };

  outputs =
    inputs:
    inputs.flake-parts.lib.mkFlake { inherit inputs; } {
      systems = [
        "aarch64-linux"
        "x86_64-linux"
      ];

      perSystem =
        { pkgs, ... }:
        {
          devShells.default = pkgs.mkShell {
            LD_LIBRARY_PATH = pkgs.lib.makeLibraryPath (
              with pkgs;
              [
                (lib.getLib stdenv.cc.cc)

                glfw3-minecraft
                openal

                alsa-lib
                libjack2
                libpulseaudio
                pipewire

                libGL
                libx11
                libxcursor
                libxext
                libxrandr
                libxxf86vm
                wayland
                libdecor

                udev

                vulkan-loader

                flite
              ]
            );
          };
        };
    };
}
