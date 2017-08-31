/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weihong.gankk.main;

import com.weihong.gankk.base.BasePresenter;
import com.weihong.gankk.base.BaseView;
import com.weihong.gankk.data.bean.GanKKInfo;
import com.weihong.gankk.util.GanKKType;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
interface MainContract {

    interface View extends BaseView<Presenter> {
        void updateData(List<GanKKInfo> ganKKInfos);
    }

    interface Presenter extends BasePresenter {
        void getData(String type, int pageSize, int page);
    }
}
