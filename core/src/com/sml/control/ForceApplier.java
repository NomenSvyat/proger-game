package com.sml.control;

import com.sml.actors.ForceAppliable;

public class ForceApplier {
    private ForceAppliable forceAppliable;
    private IForceProvider forceProvider;
    private float force;

    public ForceApplier(ForceAppliable forceAppliable, IForceProvider forceProvider) {
        this.forceAppliable = forceAppliable;
        this.forceProvider = forceProvider;
    }

    public void applyForce() {
        force = forceProvider.getForce();
        if (force != 0f) {
            forceAppliable.applyForce(force);
        }
    }
}
