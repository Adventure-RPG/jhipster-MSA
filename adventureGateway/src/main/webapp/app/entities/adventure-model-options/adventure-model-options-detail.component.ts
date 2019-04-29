import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';

@Component({
    selector: 'jhi-adventure-model-options-detail',
    templateUrl: './adventure-model-options-detail.component.html'
})
export class AdventureModelOptionsDetailComponent implements OnInit {
    adventureModelOptions: IAdventureModelOptions;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ adventureModelOptions }) => {
            this.adventureModelOptions = adventureModelOptions;
        });
    }

    previousState() {
        window.history.back();
    }
}
