import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureRace } from 'app/shared/model/adventure-race.model';

@Component({
    selector: 'jhi-adventure-race-detail',
    templateUrl: './adventure-race-detail.component.html'
})
export class AdventureRaceDetailComponent implements OnInit {
    adventureRace: IAdventureRace;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureRace }) => {
            this.adventureRace = adventureRace;
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
