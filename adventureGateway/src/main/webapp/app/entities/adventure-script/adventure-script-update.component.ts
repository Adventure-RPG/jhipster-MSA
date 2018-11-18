import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IAdventureScript } from 'app/shared/model/adventure-script.model';
import { AdventureScriptService } from './adventure-script.service';

@Component({
    selector: 'jhi-adventure-script-update',
    templateUrl: './adventure-script-update.component.html'
})
export class AdventureScriptUpdateComponent implements OnInit {
    adventureScript: IAdventureScript;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private adventureScriptService: AdventureScriptService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureScript }) => {
            this.adventureScript = adventureScript;
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

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureScript.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureScriptService.update(this.adventureScript));
        } else {
            this.subscribeToSaveResponse(this.adventureScriptService.create(this.adventureScript));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureScript>>) {
        result.subscribe((res: HttpResponse<IAdventureScript>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
