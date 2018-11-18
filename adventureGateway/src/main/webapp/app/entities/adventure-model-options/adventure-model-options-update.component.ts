import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';
import { AdventureModelOptionsService } from './adventure-model-options.service';

@Component({
    selector: 'jhi-adventure-model-options-update',
    templateUrl: './adventure-model-options-update.component.html'
})
export class AdventureModelOptionsUpdateComponent implements OnInit {
    adventureModelOptions: IAdventureModelOptions;
    isSaving: boolean;

    constructor(private adventureModelOptionsService: AdventureModelOptionsService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureModelOptions }) => {
            this.adventureModelOptions = adventureModelOptions;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureModelOptions.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureModelOptionsService.update(this.adventureModelOptions));
        } else {
            this.subscribeToSaveResponse(this.adventureModelOptionsService.create(this.adventureModelOptions));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureModelOptions>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureModelOptions>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
