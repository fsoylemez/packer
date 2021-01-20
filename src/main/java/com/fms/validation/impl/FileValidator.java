package com.fms.validation.impl;

import com.fms.exception.ApiException;
import com.fms.messages.Messages;
import com.fms.validation.Validatable;

import java.io.File;

/**
 * Check the given path to be a real,readable non empty file
 *
 * @author  Fatih Soylemez
 * @version 1.0
 * @since   2018-04-18
 */
public class FileValidator implements Validatable<String> {

    @Override
    public void validate(String filePath) {

        File file = new File(filePath);
        if(!file.exists())
           throw new ApiException(Messages.getMessage("file.not.exists"));
        if(file.isDirectory())
            throw new ApiException(Messages.getMessage("file.is.directory"));
        if(!file.canRead())
            throw new ApiException(Messages.getMessage("file.not.readable"));
        if(file.length()==0)
            throw new ApiException(Messages.getMessage("file.empty"));
    }
}
