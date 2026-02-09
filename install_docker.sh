#!/bin/bash
set -e

echo "🚀 Installing Docker Rootless (Clean Setup)..."

# Stop old daemon if running
pkill -f dockerd-rootless || true

# Remove old files
echo "🧹 Cleaning old Docker files..."
rm -rf ~/.local/share/docker
rm -rf ~/.config/systemd/user/docker.service
rm -rf ~/bin/docker*
rm -rf ~/bin/dockerd*

# Download & install rootless docker
echo "📦 Downloading Docker..."
curl -fsSL https://get.docker.com/rootless | sh

# Setup (skip iptables)
echo "⚙️ Running setup..."
~/bin/dockerd-rootless-setuptool.sh install --skip-iptables || true

# Env variables
echo "🔧 Setting environment..."

grep -qxF 'export PATH=$HOME/bin:$PATH' ~/.zshrc || \
echo 'export PATH=$HOME/bin:$PATH' >> ~/.zshrc

grep -qxF 'export DOCKER_HOST=unix://$XDG_RUNTIME_DIR/docker.sock' ~/.zshrc || \
echo 'export DOCKER_HOST=unix://$XDG_RUNTIME_DIR/docker.sock' >> ~/.zshrc

export PATH=$HOME/bin:$PATH
export DOCKER_HOST=unix://$XDG_RUNTIME_DIR/docker.sock

# Start daemon manually
echo "🔥 Starting Docker daemon..."
nohup dockerd-rootless.sh > ~/docker-rootless.log 2>&1 &

sleep 4

# Test
echo "🧪 Testing Docker..."
docker ps || echo "⚠️ Docker not responding yet. Try reopening terminal."

echo ""
echo "✅ Done!"
echo "➡️ Run: source ~/.zshrc"
echo "➡️ Then: docker ps"
