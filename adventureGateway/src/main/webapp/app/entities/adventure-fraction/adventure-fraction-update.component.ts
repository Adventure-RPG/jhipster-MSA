import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';
import { AdventureFractionService } from './adventure-fraction.service';

@Component({
    selector: 'jhi-adventure-fraction-update',
    templateUrl: './adventure-fraction-update.component.html'
})
export class AdventureFractionUpdateComponent implements OnInit {
    adventureFraction: IAdventureFraction;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private adventureFractionService: AdventureFractionService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.adventureFraction, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureFraction.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureFractionService.update(this.adventureFraction));
        } else {
            this.subscribeToSaveResponse(this.adventureFractionService.create(this.adventureFraction));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureFraction>>) {
        result.subscribe((res: HttpResponse<IAdventureFraction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
