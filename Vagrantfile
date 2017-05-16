nodes = [
  { :hostname => 'ansible', :ip => '172.16.66.10', :ram => 512, :ssh => 2210, :cpus => 1, :ports => {}},
  { :hostname => 'node01',  :ip => '172.16.66.11', :ram => 8192, :ssh => 2211, :cpus => 4, :ports => {9000 => 9000}},
  { :hostname => 'node02',  :ip => '172.16.66.12', :ram => 4096, :ssh => 2212, :cpus => 4, :ports => {}}
]

Vagrant.configure("2") do |config|
  nodes.each do |node|
    config.vm.define node[:hostname] do |nodeconfig|
      nodeconfig.vm.box = "minimal/xenial64";
      nodeconfig.vm.hostname = node[:hostname]

      nodeconfig.vm.provider :virtualbox do |vb|
        vb.customize [
          "modifyvm", :id,
          "--name", node[:hostname],
          "--memory", node[:ram].to_s,
          "--cpus", node[:cpus].to_s
        ]
      end

      nodeconfig.vm.network :private_network, ip: node[:ip], host_ip: "127.0.0.1"

      node[:ports].each do |src,dest|
        nodeconfig.vm.network :forwarded_port,
        host_ip: "127.0.0.1",
        host: src,
        guest: dest
      end

      nodeconfig.vm.network :forwarded_port,
        guest: 22,
        host: node[:ssh],
        id: "ssh",
        host_ip: "127.0.0.1",
        auto_correct: true

      # Adding public key for remote authentication if not exists
      config.vm.provision :shell, :inline => <<SCRIPT
        if grep -F -f /vagrant/environment/cert/public.key /home/vagrant/.ssh/authorized_keys; then
          echo Pubic key exists in: /home/vagrant/.ssh/authorized_keys
        else
          echo Adding public key to: /home/vagrant/.ssh/authorized_keys
          cat /vagrant/environment/cert/public.key >> /home/vagrant/.ssh/authorized_keys && chmod 600 /home/vagrant/.ssh/authorized_keys
        fi
SCRIPT

      if node[:hostname] == 'ansible'
        config.vm.provision :shell, :inline => <<SCRIPT
          apt-get -y install software-properties-common
          apt-add-repository ppa:ansible/ansible
          apt-get -y update
          apt-get -y install ansible
          cp /vagrant/environment/cert/private.key /home/vagrant/.ssh/swarm && chmod 600 /home/vagrant/.ssh/swarm && chown vagrant:vagrant /home/vagrant/.ssh/swarm
SCRIPT
      end
    end
  end
end
