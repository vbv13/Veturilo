package com.mehehe.jobs;

import java.util.concurrent.TimeUnit;
import org.knowm.sundial.annotations.SimpleTrigger;
import org.knowm.sundial.exceptions.JobInterruptException;

import static com.mehehe.DropwizardBikeApplication.VETURILO_SERVICE;

@SimpleTrigger(repeatInterval = 60, timeUnit = TimeUnit.SECONDS)
public class ReloadJob extends org.knowm.sundial.Job {

    @Override
    public void doRun() throws JobInterruptException {
        if (VETURILO_SERVICE != null) {
            VETURILO_SERVICE.reloadStations();
            System.out.println("Reloading stations");
        } else {
            System.out.println("Waiting for VETURILO_SERVICE");
        }
    }
}
