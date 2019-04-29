import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';
import { AdventureCategoryTypeService } from './adventure-category-type.service';

@Component({
    selector: 'jhi-adventure-category-type-update',
    templateUrl: './adventure-category-type-update.component.html'
})
export class AdventureCategoryTypeUpdateComponent implements OnInit {
    adventureCategoryType: IAdventureCategoryType;
    isSaving: boolean;

    constructor(protected adventureCategoryTypeService: AdventureCategoryTypeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureCategoryType }) => {
            this.adventureCategoryType = adventureCategoryType;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureCategoryType.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureCategoryTypeService.update(this.adventureCategoryType));
        } else {
            this.subscribeToSaveResponse(this.adventureCategoryTypeService.create(this.adventureCategoryType));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureCategoryType>>) {
        result.subscribe(
            (res: HttpResponse<IAdventureCategoryType>) => this.onSaveSuccess(),
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
}
