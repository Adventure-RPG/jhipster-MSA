import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureModel } from 'app/shared/model/adventure-model.model';

@Component({
    selector: 'jhi-adventure-model-detail',
    templateUrl: './adventure-model-detail.component.html'
})
export class AdventureModelDetailComponent implements OnInit {
    adventureModel: IAdventureModel;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureModel }) => {
            this.adventureModel = adventureModel;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
