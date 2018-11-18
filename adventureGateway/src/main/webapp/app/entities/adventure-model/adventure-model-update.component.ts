import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IAdventureModel } from 'app/shared/model/adventure-model.model';
import { AdventureModelService } from './adventure-model.service';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from 'app/entities/adventure-category-type';

@Component({
    selector: 'jhi-adventure-model-update',
    templateUrl: './adventure-model-update.component.html'
})
export class AdventureModelUpdateComponent implements OnInit {
    adventureModel: IAdventureModel;
    isSaving: boolean;

    adventurecategorytypes: IAdventureCategoryType[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private adventureModelService: AdventureModelService,
        private adventureCategoryTypeService: AdventureCategoryTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureModel }) => {
            this.adventureModel = adventureModel;
        });
        this.adventureCategoryTypeService.query().subscribe(
            (res: HttpResponse<IAdventureCategoryType[]>) => {
                this.adventurecategorytypes = res.body;
            },
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureModel>>) {
        result.subscribe((res: HttpResponse<IAdventureModel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackAdventureCategoryTypeById(index: number, item: IAdventureCategoryType) {
        return item.id;
    }
}
