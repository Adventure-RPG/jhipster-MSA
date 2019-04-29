import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
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
        protected jhiAlertService: JhiAlertService,
        protected adventureRaceOptionsService: AdventureRaceOptionsService,
        protected adventureModelOptionsService: AdventureModelOptionsService,
        protected adventureModelService: AdventureModelService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureRaceOptions }) => {
            this.adventureRaceOptions = adventureRaceOptions;
        });
        this.adventureModelOptionsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureModelOptions[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureModelOptions[]>) => response.body)
            )
            .subscribe(
                (res: IAdventureModelOptions[]) => (this.adventuremodeloptions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.adventureModelService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureModel[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureModel[]>) => response.body)
            )
            .subscribe((res: IAdventureModel[]) => (this.adventuremodels = res), (res: HttpErrorResponse) => this.onError(res.message));
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

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureRaceOptions>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureRaceOptions>) => this.onSaveSuccess(),
            (res: HttpErrorResponse) => this.onSaveError()
        );
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
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
