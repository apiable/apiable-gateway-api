# Introduction
This is the API which represents the communication towards certain Gateway.

## How-To
The concrete implementation of a certain Gateway needs to be implemented towards the [Gateway](src/main/kotlin/io/apiable/gateways/adapter/Gateway.kt) interface.

Modify the [Conf](src/main/kotlin/io/apiable/gateways/adapter/model/Conf.kt) to your needs by forking this project and creating a pull request when finished.

Any Feedback, adoptions and suggestions to make our developer life easier are very much appreciated and can be recommended in a forked pull request as well.

## Example
```(kotlin)
class AmazonGateway() : Gateway {

    override fun listApis(conf: Conf): List<Api>: String {
        require(conf is AmazonBasicConf)
        ... specific implementation ...
    }
    ...
}
``
