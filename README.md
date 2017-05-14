# Feeliks

`environment/cert/private.key` and `environment/cert/public.key` are used to enable Ansible to connect to other machines - you can generate you own key pair.

## Prerequisites
Install to your host machine:
- [VirtualBox](https://www.virtualbox.org/)
- [Vagrant](https://www.vagrantup.com/)

## Setup

```sh
# Clone repo
git clone https://github.com/raitraidma/feeliks.git
cd feeliks

# Create VMs
vagrant up

# Login into ansible's VM
vagrant ssh ansible
# or use Putty: 127.0.0.1:2210 vagrant/vagrant

# Create Docker Swarm
cd /vagrant/environment
./setup.sh
exit

# Deploy services
vagrant ssh node01
# or use Putty: 127.0.0.1:2211 vagrant/vagrant
cd /vagrant/environment
./deploy.sh
```

## Management and monitoring
When Portainer service comes up, then you can monitor docker services from `http://127.0.0.1:9000`.
This may take some time. So far you can use docker commands to monitor your environment.

In node01 (which is Docker master), you can monitor environment with such commands:
```sh
# List all nodes in the swarm
docker node ls

# List services in stack feeliks (there you can see if services are already up and running)
docker stack services feeliks

# List the tasks of a service (there you can see what is the current state of the task also)
docker service ps <service id>

# Visit Documentation of Docker for more information: https://docs.docker.com/
```

## Development
For development you need to install:
- [Java JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/), if you want to build images in host machine

### Build images in VM
You do not have to install any additional software for that.
```sh
vagrant ssh node01
cd /vagrant

# Build all modules
mvn clean package docker:build

# Build one module
mvn clean package docker:build -pl stt -am
```
