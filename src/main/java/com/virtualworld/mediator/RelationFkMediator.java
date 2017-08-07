/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virtualworld.mediator;

import com.virtualworld.mediator.listeners.MediatorEvent;
import com.virtualworld.mediator.listeners.MediatorEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ulrich
 */
public class RelationFkMediator implements MediatorEvent {

    private static final List<MediatorEventListener> MEDIATOR_EVENT_LISTENERS = new ArrayList<>();
    private static RelationFkMediator INSTANCE = null;

    private RelationFkMediator() {
    }

    public static RelationFkMediator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RelationFkMediator();
        }
        return INSTANCE;
    }

    @Override
    public void addMediatorEventListener(MediatorEventListener mediatorEventListener) {
        MEDIATOR_EVENT_LISTENERS.add(mediatorEventListener);
    }

    @Override
    public void updateMediatorListeners() {
        for (MediatorEventListener mel : MEDIATOR_EVENT_LISTENERS) {
            mel.onRelationChange();
        }
    }

    @Override
    public void removeMediatorEventListener(MediatorEventListener mediatorEventListener) {
        MEDIATOR_EVENT_LISTENERS.remove(mediatorEventListener);
    }

}
