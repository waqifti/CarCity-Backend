package com.carcity.CarCity.Backend;

import com.carcity.CarCity.Backend.dataentities.*;
import com.carcity.CarCity.Backend.projections.ProjectionOnlyUser;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class JobsManager implements DisposableBean, Runnable {
    @Autowired
    JobsRepo objJobsRepo;

    @Autowired
    ApplicationUserRepo objApplicationUserRepo;

    @Autowired
    PushNotificationUtil objPushNotificationUtil;

    private Thread thread;

    @PostConstruct
    public void init(){
        this.thread = new Thread(this);
        this.thread.start();
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(30000);
                List<Jobs> jobs=objJobsRepo.findAllByState(JobState.NEW_JOB_WANTS_SERVICE_NOW);
                if(jobs!=null){
                    for(Jobs i: jobs){
                        if(i.getAssignedto()==null){
                            //Not Assigned Job
                            System.out.println("Will try to assign sp to job id "+i.getId()+" created by "+i.getCreatedby().getCell());

                            Set<Integer> spIds=objApplicationUserRepo.getAllSps(UserTypes.ServiceProvider);

                            if(spIds!=null && spIds.size()>0){
                                System.out.println("List Of All Sps = "+spIds.toString());

                                Set<Integer> spsAlreadyBusy=objJobsRepo.getIdsOfAssignedUsers();

                                if(spsAlreadyBusy!=null && spsAlreadyBusy.size()>0){
                                    System.out.println("List Of All Sps That Are busy = "+spsAlreadyBusy.toString());
                                    spIds.removeAll(spsAlreadyBusy);
                                    System.out.println("List Of All Sps That Are avaialble = "+spIds.toString());

                                } else {


                                }

                                ApplicationUser toAssign = objApplicationUserRepo.getById(spIds.iterator().next());
                                i.setAssignedto(toAssign);
                                i.setState(JobState.JOB_ASSIGNED_TO_SP);
                                objJobsRepo.saveAndFlush(i);
                                System.out.println("Assigned "+toAssign.getCell());
                                if(i.getCreatedby().getFcmtoken()!=null && !i.getCreatedby().getFcmtoken().trim().isEmpty()) {
                                    try {
                                        objPushNotificationUtil.sendNotificationToAndroid(i.getCreatedby().getFcmtoken(),
                                                "Car City",
                                                "Aap ko sp assign kur diya gya hay.");
                                    } catch (Exception ex) {

                                    }

                                }

                                if(i.getAssignedto().getFcmtoken()!=null && !i.getAssignedto().getFcmtoken().trim().isEmpty()) {
                                    try {
                                        objPushNotificationUtil.sendNotificationToAndroid(i.getAssignedto().getFcmtoken(),
                                                "Car City",
                                                "Aap ko new job kur di gye hay.");
                                    } catch (Exception ex) {

                                    }

                                }

                            } else {
                                System.out.println("Sps not available to assign");
                            }







                        }
                    }
                }



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
