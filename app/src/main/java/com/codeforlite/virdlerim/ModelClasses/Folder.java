package com.codeforlite.virdlerim.ModelClasses;

import com.codeforlite.virdlerim.ModelClasses.Vird_Classes.Vird;

import java.util.ArrayList;

public class Folder {

    protected String folderName;
    protected ArrayList<Vird> containingVirds;
    protected int backgroundId;
    protected String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Folder(String folderName,String kind) {
        this.folderName = folderName;
        this.kind=kind;
    }

    public Folder(String folderName,String kind, ArrayList<Vird> containingVirds) {
        this.folderName = folderName;
        this.containingVirds = containingVirds;
        this.kind=kind;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public ArrayList<Vird> getContainingVirds() {
        return containingVirds;
    }

    public void setContainingVirds(ArrayList<Vird> containingVirds) {
        this.containingVirds = containingVirds;
    }

    public int getBackgroundId() {
        return backgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.backgroundId = backgroundId;
    }
}
