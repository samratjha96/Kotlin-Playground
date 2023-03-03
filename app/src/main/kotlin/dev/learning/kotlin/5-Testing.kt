package dev.learning.kotlin

class `5-Testing` {
}

class Ec2Client {

    fun createInstance() {
        println("Creating instance")
    }

    fun deleteInstance() {
        println("Deleting instance")
    }
}

class Ec2ClientUser(val ec2Client: Ec2Client) {
    fun doThing() {
        ec2Client.createInstance()
        if(2 + 3 + 9 == 14) {
            ec2Client.deleteInstance()
        }
        ec2Client.deleteInstance()
    }
}