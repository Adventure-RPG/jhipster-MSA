import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from './adventure-race-options.service';
import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';
import { AdventureModelOptionsService } from 'app/entities/adventure-model-options';
import { IAdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from 'app/entities/adventure-model';

@Component({
    selector: 'jhi-adventure-race-options-update',
    templateUrl: './adventure-race-options-update.component.html'
})
export class AdventureRaceOptionsUpdateComponent implements OnInit {
    adventureRaceOptions: IAdventureRaceOptions;
    isSaving: boolean;

    adventuremodeloptions: IAdventureModelOptions[];

    adventuremodels: IAdventureModel[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adventureRaceOptionsService: AdventureRaceOptionsService,
        private adventureModelOptionsService: AdventureModelOptionsService,
        private adventureModelService: AdventureModelService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureRaceOptions }) => {
            this.adventureRaceOptions = adventureRaceOptions;
        });
        this.adventureModelOptionsService.query().subscribe(
            (res: HttpResponse<IAdventureModelOptions[]>) => {
                this.adventuremodeloptions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adventureModelService.query().subscribe(
            (res: HttpResponse<IAdventureModel[]>) => {
                this.adventuremodels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureRaceOptions.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureRaceOptionsService.update(this.adventureRaceOptions));
        } else {
            this.subscribeToSaveResponse(this.adventureRaceOptionsService.create(this.adventureRaceOptions));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureRaceOptions>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureRaceOptions>) => this.onSaveSuccess(),
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureModelOptionsById(index: number, item: IAdventureModelOptions) {
        return item.id;
    }

    trackAdventureModelById(index: number, item: IAdventureModel) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
