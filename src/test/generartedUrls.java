package test;

import com.dogiloki.multitaks.dataformat.JSON;
import com.dogiloki.multitaks.directory.Storage;
import java.util.List;
import java.util.Arrays;

/**
 *
 * @author dogi_
 */

public class generartedUrls{
    
    public generartedUrls(){
        List urls=Arrays.asList(
            new Instalacion("1.2.5","https://launcher.mojang.com/v1/objects/d8321edc9470e56b8ad5c67bbd16beba25843336/server.jar"),
            new Instalacion("1.3.1",""),
            new Instalacion("1.3.2",""),
            new Instalacion("1.4.2",""),
            new Instalacion("1.4.4",""),
            new Instalacion("1.4.5",""),
            new Instalacion("1.4.6",""),
            new Instalacion("1.4.7",""),
            new Instalacion("1.5.1",""),
            new Instalacion("1.5.2",""),
            new Instalacion("1.6.1",""),
            new Instalacion("1.6.2",""),
            new Instalacion("1.6.4",""),
            new Instalacion("1.7.2",""),
            new Instalacion("1.7.3",""),
            new Instalacion("1.7.4",""),
            new Instalacion("1.7.5",""),
            new Instalacion("1.7.6",""),
            new Instalacion("1.7.7","https://launcher.mojang.com/v1/objects/a6ffc1624da980986c6cc12a1ddc79ab1b025c62/server.jar"),
            new Instalacion("1.7.8","https://launcher.mojang.com/v1/objects/d8321edc9470e56b8ad5c67bbd16beba25843336/server.jar"),
            new Instalacion("1.7.9","https://launcher.mojang.com/v1/objects/4cec86a928ec171fdc0c6b40de2de102f21601b5/server.jar"),
            new Instalacion("1.7.10","https://launcher.mojang.com/v1/objects/952438ac4e01b4d115c5fc38f891710c4941df29/server.jar"),
            new Instalacion("1.8","https://launcher.mojang.com/v1/objects/a028f00e678ee5c6aef0e29656dca091b5df11c7/server.jar"),
            new Instalacion("1.8.1","https://launcher.mojang.com/v1/objects/68bfb524888f7c0ab939025e07e5de08843dac0f/server.jar"),
            new Instalacion("1.8.2","https://launcher.mojang.com/v1/objects/a37bdd5210137354ed1bfe3dac0a5b77fe08fe2e/server.jar"),
            new Instalacion("1.8.3","https://launcher.mojang.com/v1/objects/163ba351cb86f6390450bb2a67fafeb92b6c0f2f/server.jar"),
            new Instalacion("1.8.4","https://launcher.mojang.com/v1/objects/dd4b5eba1c79500390e0b0f45162fa70d38f8a3d/server.jar"),
            new Instalacion("1.8.5","https://launcher.mojang.com/v1/objects/ea6dd23658b167dbc0877015d1072cac21ab6eee/server.jar"),
            new Instalacion("1.8.6","https://launcher.mojang.com/v1/objects/2bd44b53198f143fb278f8bec3a505dad0beacd2/server.jar"),
            new Instalacion("1.8.7","https://launcher.mojang.com/v1/objects/35c59e16d1f3b751cd20b76b9b8a19045de363a9/server.jar"),
            new Instalacion("1.8.8","https://launcher.mojang.com/v1/objects/5fafba3f58c40dc51b5c3ca72a98f62dfdae1db7/server.jar"),
            new Instalacion("1.8.9","https://launcher.mojang.com/v1/objects/b58b2ceb36e01bcd8dbf49c8fb66c55a9f0676cd/server.jar"),
            new Instalacion("1.9","https://launcher.mojang.com/v1/objects/b4d449cf2918e0f3bd8aa18954b916a4d1880f0d/server.jar"),
            new Instalacion("1.9.1","https://launcher.mojang.com/v1/objects/bf95d9118d9b4b827f524c878efd275125b56181/server.jar"),
            new Instalacion("1.9.2","https://launcher.mojang.com/v1/objects/2b95cc7b136017e064c46d04a5825fe4cfa1be30/server.jar"),
            new Instalacion("1.9.3","https://launcher.mojang.com/v1/objects/8e897b6b6d784f745332644f4d104f7a6e737ccf/server.jar"),
            new Instalacion("1.9.4","https://launcher.mojang.com/v1/objects/edbb7b1758af33d365bf835eb9d13de005b1e274/server.jar"),
            new Instalacion("1.10","https://launcher.mojang.com/v1/objects/a96617ffdf5dabbb718ab11a9a68e50545fc5bee/server.jar"),
            new Instalacion("1.10.1","https://launcher.mojang.com/v1/objects/cb4c6f9f51a845b09a8861cdbe0eea3ff6996dee/server.jar"),
            new Instalacion("1.10.2","https://launcher.mojang.com/v1/objects/3d501b23df53c548254f5e3f66492d178a48db63/server.jar"),
            new Instalacion("1.11","https://launcher.mojang.com/v1/objects/48820c84cb1ed502cb5b2fe23b8153d5e4fa61c0/server.jar"),
            new Instalacion("1.11.1","https://launcher.mojang.com/v1/objects/48820c84cb1ed502cb5b2fe23b8153d5e4fa61c0/server.jar"),
            new Instalacion("1.11.2","https://launcher.mojang.com/v1/objects/f00c294a1576e03fddcac777c3cf4c7d404c4ba4/server.jar"),
            new Instalacion("1.12","https://launcher.mojang.com/v1/objects/8494e844e911ea0d63878f64da9dcc21f53a3463/server.jar",new Instalacion("14.23.5.2859","https://maven.minecraftforge.net/net/minecraftforge/forge/1.12.2-14.23.5.2859/forge-1.12.2-14.23.5.2859-universal.jar")),
            new Instalacion("1.12.1","https://launcher.mojang.com/v1/objects/561c7b2d54bae80cc06b05d950633a9ac95da816/server.jar"),
            new Instalacion("1.12.2","https://launcher.mojang.com/v1/objects/886945bfb2b978778c3a0288fd7fab09d315b25f/server.jar"),
            new Instalacion("1.13","https://launcher.mojang.com/v1/objects/d0caafb8438ebd206f99930cfaecfa6c9a13dca0/server.jar"),
            new Instalacion("1.13.1","https://launcher.mojang.com/v1/objects/fe123682e9cb30031eae351764f653500b7396c9/server.jar"),
            new Instalacion("1.13.2","https://launcher.mojang.com/v1/objects/3737db93722a9e39eeada7c27e7aca28b144ffa7/server.jar"),
            new Instalacion("1.14","https://launcher.mojang.com/v1/objects/f1a0073671057f01aa843443fef34330281333ce/server.jar"),
            new Instalacion("1.14.1","https://launcher.mojang.com/v1/objects/ed76d597a44c5266be2a7fcd77a8270f1f0bc118/server.jar"),
            new Instalacion("1.14.2","https://launcher.mojang.com/v1/objects/808be3869e2ca6b62378f9f4b33c946621620019/server.jar"),
            new Instalacion("1.14.3","https://launcher.mojang.com/v1/objects/d0d0fe2b1dc6ab4c65554cb734270872b72dadd6/server.jar"),
            new Instalacion("1.14.4","https://launcher.mojang.com/v1/objects/3dc3d84a581f14691199cf6831b71ed1296a9fdf/server.jar"),
            new Instalacion("1.15","https://launcher.mojang.com/v1/objects/e9f105b3c5c7e85c7b445249a93362a22f62442d/server.jar"),
            new Instalacion("1.15.1","https://launcher.mojang.com/v1/objects/4d1826eebac84847c71a77f9349cc22afd0cf0a1/server.jar"),
            new Instalacion("1.15.2","https://launcher.mojang.com/v1/objects/bb2b6b1aefcd70dfd1892149ac3a215f6c636b07/server.jar"),
            new Instalacion("1.16","https://launcher.mojang.com/v1/objects/a0d03225615ba897619220e256a266cb33a44b6b/server.jar"),
            new Instalacion("1.16.1","https://launcher.mojang.com/v1/objects/a0d03225615ba897619220e256a266cb33a44b6b/server.jar"),
            new Instalacion("1.16.2","https://launcher.mojang.com/v1/objects/c5f6fb23c3876461d46ec380421e42b289789530/server.jar"),
            new Instalacion("1.16.3","https://launcher.mojang.com/v1/objects/f02f4473dbf152c23d7d484952121db0b36698cb/server.jar"),
            new Instalacion("1.16.4","https://launcher.mojang.com/v1/objects/35139deedbd5182953cf1caa23835da59ca3d7cd/server.jar"),
            new Instalacion("1.16.5","https://launcher.mojang.com/v1/objects/1b557e7b033b583cd9f66746b7a9ab1ec1673ced/server.jar"),
            new Instalacion("1.17","https://launcher.mojang.com/v1/objects/0a269b5f2c5b93b1712d0f5dc43b6182b9ab254e/server.jar"),
            new Instalacion("1.17.1","https://launcher.mojang.com/v1/objects/a16d67e5807f57fc4e550299cf20226194497dc2/server.jar"),
            new Instalacion("1.18","https://launcher.mojang.com/v1/objects/3cf24a8694aca6267883b17d934efacc5e44440d/server.jar"),
            new Instalacion("1.18.1","https://launcher.mojang.com/v1/objects/125e5adf40c659fd3bce3e66e67a16bb49ecc1b9/server.jar"),
            new Instalacion("1.18.2","https://launcher.mojang.com/v1/objects/c8f83c5655308435b3dcf03c06d9fe8740a77469/server.jar"),
            new Instalacion("1.19","https://launcher.mojang.com/v1/objects/e00c4052dac1d59a1188b2aa9d5a87113aaf1122/server.jar"),
            new Instalacion("1.19.1","https://piston-data.mojang.com/v1/objects/8399e1211e95faa421c1507b322dbeae86d604df/server.jar"),
            new Instalacion("1.19.2","https://piston-data.mojang.com/v1/objects/f69c284232d7c7580bd89a5a4931c3581eae1378/server.jar")
        );
        //new Storage("servers.json").write(new JSON(urls).toString());
    }
    
    public static void main(String[] args){
        new generartedUrls();
    }
}

class Instalacion{
    public String version;
    public String url;
    public String adler32; // Comprobar integridad del archivo (próximamente)
    public Instalacion forge;
    public Instalacion(String version, String url, Instalacion forge){
        this.version=version;
        this.url=url;
        this.forge=forge;
    }
    public Instalacion(String version, String url){
        this.version=version;
        this.url=url;
    }
}