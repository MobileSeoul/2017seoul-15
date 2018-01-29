package com.stm.user.create.opinion.interactor;

import com.stm.common.dao.Opinion;
import com.stm.common.dao.OpinionCategory;
import com.stm.common.dao.User;

/**
 * Created by Dev-0 on 2017-08-31.
 */

public interface OpinionInteractor {
    void setOpinionRepository(String accessToken);

    User getUser();

    void setUser(User user);

    OpinionCategory getOpinionCategory();

    void setOpinionCategory(OpinionCategory opinionCategory);

    void insertOpinion(Opinion opinion);
}
