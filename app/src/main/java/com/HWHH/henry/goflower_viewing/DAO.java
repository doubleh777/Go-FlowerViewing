package com.HWHH.henry.goflower_viewing;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Henry on 2015. 3. 25..
 */
public class DAO {

    private MusicArrayList playListV1;
    private MusicArrayList playListV2;
    private Context context;
    private final String MUSIC_LIST_VERSION_1 = "MusicListVersion1.xml";
    private final String MUSIC_LIST_VERSION_2 = "MusicListVersion2.xml";


    public DAO(Context context){

        playListV1 = new MusicArrayList();
        playListV2= new MusicArrayList();
        AssetManager assetManager = context.getAssets();

        //list1
        try {
            InputStream inputStream = assetManager.open(MUSIC_LIST_VERSION_1);
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList musicList = doc.getElementsByTagName("Music");
            Log.i("test",doc.getDocumentElement().getNodeName());
            for(int i = 0 ; i < musicList.getLength() ; i++){
                Node musicNode = musicList.item(i);

                if(musicNode.getNodeType() == Node.ELEMENT_NODE){
                    Element musicElement = (Element)musicNode;
                    NodeList musicIdNodeList = musicElement.getElementsByTagName("id");
                    Element musicIdElement = (Element)musicIdNodeList.item(0);
                    Node musicIdNode = musicIdElement.getFirstChild();
                    String musicId = musicIdNode.getNodeValue();

                    NodeList musicTitleNodeList = musicElement.getElementsByTagName("title");
                    Element musicTitleElement = (Element)musicTitleNodeList.item(0);
                    Node musicTitleNode = musicTitleElement.getFirstChild();
                    String musicTitle = musicTitleNode.getNodeValue();
                    Log.i("test",musicId);
                    playListV1.add(new Music(musicId, musicTitle));
                }
            }

        }catch(IOException e){
            Log.e("Error", "IOException occured");
        }catch(ParserConfigurationException e){
            Log.e("Error", "ParserConfigurationException occured");
        }catch(SAXException e){
            Log.e("Error", "SAXException");
        }

        //list2
        try {
            InputStream inputStream = assetManager.open(MUSIC_LIST_VERSION_2);
            DocumentBuilderFactory docBuildFact = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuild = docBuildFact.newDocumentBuilder();
            Document doc = docBuild.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList musicList = doc.getElementsByTagName("Music");

            for(int i = 0 ; i < musicList.getLength() ; i++){
                Node musicNode = musicList.item(i);

                if(musicNode.getNodeType() == Node.ELEMENT_NODE){
                    Element musicElement = (Element)musicNode;
                    NodeList musicIdNodeList = musicElement.getElementsByTagName("id");
                    Element musicIdElement = (Element)musicIdNodeList.item(0);
                    Node musicIdNode = musicIdElement.getFirstChild();
                    String musicId = musicIdNode.getNodeValue();

                    NodeList musicTitleNodeList = musicElement.getElementsByTagName("title");
                    Element musicTitleElement = (Element)musicTitleNodeList.item(0);
                    Node musicTitleNode = musicTitleElement.getFirstChild();
                    String musicTitle = musicTitleNode.getNodeValue();
                    Log.i("test",musicId);
                    playListV2.add(new Music(musicId, musicTitle));
                }
            }

        }catch(IOException e){
            Log.e("Error", "IOException occured");
        }catch(ParserConfigurationException e){
            Log.e("Error", "ParserConfigurationException occured");
        }catch(SAXException e){
            Log.e("Error", "SAXException");
        }
    }

    public MusicArrayList getPlayListV1(){
        return playListV1;
    }

    public MusicArrayList getPlayListV2(){
        return playListV2;
    }
}
