import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';

@Component({
    selector: 'jhi-adventure-fraction-detail',
    templateUrl: './adventure-fraction-detail.component.html'
})
export class AdventureFractionDetailComponent implements OnInit {
    adventureFraction: IAdventureFraction;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureFraction }) => {
            this.adventureFraction = adventureFraction;
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
