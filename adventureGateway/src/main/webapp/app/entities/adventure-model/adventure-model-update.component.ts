import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from './adventure-model.service';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from 'app/entities/adventure-category-type';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';
import { AdventureRaceOptionsService } from 'app/entities/adventure-race-options';

@Component({
    selector: 'jhi-adventure-model-update',
    templateUrl: './adventure-model-update.component.html'
})
export class AdventureModelUpdateComponent implements OnInit {
    adventureModel: IAdventureModel;
    isSaving: boolean;

    adventurecategorytypes: IAdventureCategoryType[];

    adventureraceoptions: IAdventureRaceOptions[];

    constructor(
        protected dataUtils: JhiDataUtils,
        protected jhiAlertService: JhiAlertService,
        protected adventureModelService: AdventureModelService,
        protected adventureCategoryTypeService: AdventureCategoryTypeService,
        protected adventureRaceOptionsService: AdventureRaceOptionsService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureModel }) => {
            this.adventureModel = adventureModel;
        });
        this.adventureCategoryTypeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureCategoryType[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureCategoryType[]>) => response.body)
            )
            .subscribe(
                (res: IAdventureCategoryType[]) => (this.adventurecategorytypes = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.adventureRaceOptionsService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureRaceOptions[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureRaceOptions[]>) => response.body)
            )
            .subscribe(
                (res: IAdventureRaceOptions[]) => (this.adventureraceoptions = res),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureModel.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureModelService.update(this.adventureModel));
        } else {
            this.subscribeToSaveResponse(this.adventureModelService.create(this.adventureModel));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureModel>>) {
        result.subscribe((res: HttpResponse<IAdventureModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAdventureCategoryTypeById(index: number, item: IAdventureCategoryType) {
        return item.id;
    }

    trackAdventureRaceOptionsById(index: number, item: IAdventureRaceOptions) {
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
