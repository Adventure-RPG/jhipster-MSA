import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAdventureScript, AdventureScript } from 'app/shared/model/adventure-script.model';
import { AdventureScriptService } from './adventure-script.service';

@Component({
  selector: 'jhi-adventure-script-update',
  templateUrl: './adventure-script-update.component.html'
})
export class AdventureScriptUpdateComponent implements OnInit {
  adventureScript: IAdventureScript;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    file: [],
    fileContentType: [],
    argumentsScript: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected adventureScriptService: AdventureScriptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ adventureScript }) => {
      this.updateForm(adventureScript);
      this.adventureScript = adventureScript;
    });
  }

  updateForm(adventureScript: IAdventureScript) {
    this.editForm.patchValue({
      id: adventureScript.id,
      name: adventureScript.name,
      file: adventureScript.file,
      fileContentType: adventureScript.fileContentType,
      argumentsScript: adventureScript.argumentsScript
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const adventureScript = this.createFromForm();
    if (adventureScript.id !== undefined) {
      this.subscribeToSaveResponse(this.adventureScriptService.update(adventureScript));
    } else {
      this.subscribeToSaveResponse(this.adventureScriptService.create(adventureScript));
    }
  }

  private createFromForm(): IAdventureScript {
    const entity = {
      ...new AdventureScript(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      fileContentType: this.editForm.get(['fileContentType']).value,
      file: this.editForm.get(['file']).value,
      argumentsScript: this.editForm.get(['argumentsScript']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureScript>>) {
    result.subscribe((res: HttpResponse<IAdventureScript>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
