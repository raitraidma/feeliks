#!/bin/bash

ansible-playbook ansible/playbook.yml -i ansible/inventory -e @ansible/vars.yml