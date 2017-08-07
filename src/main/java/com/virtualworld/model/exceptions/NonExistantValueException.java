/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.model.exceptions;

/**
 *
 * @author Ulrich
 */
public class NonExistantValueException extends Exception {

    public NonExistantValueException(String message) {
        super(message);
    }

    public NonExistantValueException(Throwable cause) {
        super(cause);
    }

}
