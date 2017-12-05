package co.nextwireless.swagger

import io.swagger.codegen.languages.Swift3Codegen

/**
 * Created with IntelliJ IDEA.
 * User: Victor Mercurievv
 * Date: 6/19/2017
 * Time: 5:10 PM
 * Contacts: email: mercurievvss@gmail.com Skype: 'grobokopytoff' or 'mercurievv'
 */
class NWSwift3Codegen extends Swift3Codegen {
    @Override
    String toApiName(String name) {
        if (name.length() == 0)
            return super.toApiName(name)
        return camelize(name) + "API"
    }
}
