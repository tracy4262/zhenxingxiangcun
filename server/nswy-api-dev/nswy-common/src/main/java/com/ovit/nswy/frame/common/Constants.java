package com.ovit.nswy.frame.common;

/**
 * Created by ${huipei.x} on 2017-10-23.
 */
public interface Constants {

    //String SPECIES_INDEX="species";
    String SPECIES_INDEX="species_info";
    String SPECIES_TYPE="Species";
    String SPECIESCLASSIFIED_INDEX="speciesclassified";
    String SPECIESCLASSIFIED_TYPE="Speciesclassified";
    String JWT_KEY_USER_ID = "userAccount";
    String JWT_KEY_NAME = "userName";
    String JWT_KEY_CLAIMS = "claims";
    Integer DEFAUL_PAGE_SIZE = 10;

    String SESSION_SALT = "sessionSALT";
    //用户已存在
    String EXISTS = "exists";

    String NOT_EXISTS = "not_exists";

    interface IndexConstant {
        String SPECIESCOMMENT_INDEX="species_comment";
        String SPECIESCOMMENT_TYPE="species_comment";
        String SPECIESVARIETEY_INDEX="species_varietey";
        String SPECIESVARIETEY_TYPE="species_varietey";
        String SPECIESDISEASE_INDEX="species_disease";
        String SPECIESDISEASE_TYPE="species_disease";
        String SPECIESPEST_INDEX="species_pest";
        String SPECIESPEST_TYPE="species_pest";
        String SPECIEWIKI_INDEX="species_wiki";
        String  SPECIEWIKI_TYPE="species_wiki";
        String SPECIECLASS_INDEX="species_class";
        String  SPECICLASS_TYPE="species_class";
        String SPECIESPROPERTY_INDEX="species_property";
        String SPECIESPROPERTY_TYPE="species_property";
    }

}
