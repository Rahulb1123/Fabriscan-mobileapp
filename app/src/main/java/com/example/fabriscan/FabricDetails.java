package com.example.fabriscan;

public class FabricDetails {

    public static String getDescription(String fabricName) {
        switch (fabricName.toLowerCase()) {
            case "acrylic":
                return "Acrylic is a synthetic fiber known for its softness, warmth, and wool-like feel. It's lightweight, resistant to moths and chemicals, and holds color well.";
            case "artificial_fur":
                return "Artificial fur is a synthetic fabric designed to resemble real animal fur. It is widely used in fashion and home decor for its warmth and luxurious appearance without harming animals.";
            case "artificial_leather":
                return "Artificial leather, or faux leather, is a synthetic material designed to look like real leather. It's a durable, cost-effective, and vegan alternative used in clothing, upholstery, and accessories.";
            case "blended":
                return "Blended fabrics are created by combining two or more different fibers to achieve enhanced properties, such as improved strength, texture, or wrinkle resistance.";
            case "chenille":
                return "Chenille is a soft, fuzzy fabric known for its durability and plush texture. It's commonly used for upholstery, blankets, and comfortable clothing like sweaters.";
            case "corduroy":
                return "Corduroy is a durable, ridged fabric, easily recognizable by its distinct cord-like pattern. It is often used for making trousers, jackets, and shirts.";
            case "cotton":
                return "Cotton is a soft, breathable natural fiber. It is one of the most common materials for clothing and textiles due to its comfort and versatility.";
            case "crepe":
                return "Crepe is a lightweight, twisted-weave fabric with a crinkled, bumpy surface. It is often used for dresses, blouses, and evening wear.";
            case "denim":
                return "Denim is a sturdy, cotton twill fabric, most commonly used for jeans, jackets, and other workwear. It's known for its durability and classic style.";
            case "felt":
                return "Felt is a non-woven fabric made by matting, condensing, and pressing fibers together. It's used in crafts, hats, and industrial applications.";
            case "fleece":
                return "Fleece is a soft, lightweight, and warm synthetic fabric, often made from polyester. It's a popular choice for jackets, blankets, and outdoor clothing.";
            case "fur":
                return "Fur is the thick growth of hair that covers the skin of many animals. It is used in clothing for its warmth and luxurious feel, though ethical concerns are common.";
            case "leather":
                return "Leather is a durable and flexible material created by tanning animal rawhide and skins. It's widely used for footwear, clothing, and furniture.";
            case "linen":
                return "Linen is a strong, lightweight fabric made from the flax plant. It is known for its exceptional coolness and freshness in hot weather.";
            case "nylon":
                return "Nylon is a strong, lightweight, and elastic synthetic polymer. It is used in a wide variety of applications, from clothing and ropes to engineering plastics.";
            case "polyester":
                return "Polyester is a durable, wrinkle-resistant synthetic fabric that dries quickly. It is widely used in clothing, home furnishings, and industrial fabrics.";
            case "satin":
                return "Satin is a fabric with a glossy surface and a dull back, created with a specific weaving technique. It's often used for lingerie, evening gowns, and bedding.";
            case "silk":
                return "Silk is a natural protein fiber produced by silkworms. It is renowned for its softness, luster, and luxurious feel, making it a prized material for high-end clothing.";
            case "suede":
                return "Suede is a type of leather with a napped, fuzzy finish. It's softer and less durable than traditional leather, often used for jackets, shoes, and delicate accessories.";
            case "terrycloth":
                return "Terrycloth is a highly absorbent fabric with uncut loops on one or both sides. It is most commonly used for towels, robes, and bathmats.";
            case "velvet":
                return "Velvet is a soft, luxurious fabric characterized by a dense pile of evenly cut fibers. It has a distinctive soft feel and is used for clothing and upholstery.";
            case "viscose":
                return "Viscose is a semi-synthetic rayon fabric made from wood pulp. It has a silky feel and drapes well, often used as a silk substitute in clothing.";
            case "wool":
                return "Wool is a natural fiber obtained from sheep and other animals. It is known for its warmth, durability, and moisture-wicking properties.";
            default:
                return "No description available for this fabric type. This may be an unclassified or utility material.";
        }
    }

    public static String getRecommendations(String fabricName) {
        switch (fabricName.toLowerCase()) {
            case "acrylic":
                return "Printed, Jacquard, Embossed, Pleated, Knitted, Applique";
            case "artificial_fur":
                return "Embossed, Animal Print, Patchwork, Color Blocking, Shaved, Dyed";
            case "artificial_leather":
                return "Laser Cut, Embossed, Printed, Quilted, Perforated, Studded";
            case "blended":
                return "Printed, Embroidery, Jacquard, Applique, Tie-Dye, Pleated";
            case "chenille":
                return "Jacquard, Embroidery, Printed, Tufted, Pleated, Patchwork";
            case "corduroy":
                return "Embroidery, Printed, Applique, Washed, Patchwork, Ribbed";
            case "cotton":
                return "Printed, Embroidery, Applique, Tie-Dye, Smocking, Quilting";
            case "crepe":
                return "Printed, Embroidery, Digital Print, Pleated, Ruffled, Beaded";
            case "denim":
                return "Washed, Printed, Embroidery, Distressed, Patchwork, Applique";
            case "felt":
                return "Applique, Printed, Cutwork, Layered, Stitched, Beaded";
            case "fleece":
                return "Printed, Embroidery, Patchwork, Applique, Tie-Dye, Embossed";
            case "fur":
                return "Embossed, Animal Print, Patchwork, Color Blocking, Shaved, Dyed";
            case "leather":
                return "Laser Cut, Embossed, Printed, Quilted, Perforated, Studded";
            case "linen":
                return "Printed, Embroidery, Applique, Drawn Thread Work, Hemstitching, Pleated";
            case "nylon":
                return "Printed, Digital Print, Embossed, Pleated, Quilted, Heat-Set";
            case "polyester":
                return "Printed, Digital Print, Embroidery, Pleated, Sublimation, Embossed";
            case "satin":
                return "Embroidery, Printed, Jacquard, Beaded, Pleated, Ruffled";
            case "silk":
                return "Printed, Embroidery, Digital Print, Shibori, Batik, Beaded";
            case "suede":
                return "Embossed, Laser Cut, Printed, Fringed, Perforated, Stitched";
            case "terrycloth":
                return "Jacquard, Printed, Embroidery, Applique, Woven Patterns, Tie-Dye";
            case "velvet":
                return "Embroidery, Printed, Jacquard, Burnout, Devoré, Pleated";
            case "viscose":
                return "Printed, Digital Print, Embroidery, Tie-Dye, Batik, Pleated";
            case "wool":
                return "Knitted, Printed, Embroidery, Felted, Woven Patterns, Tweed";
            default:
                return "No recommendations available for this fabric type.";
        }
    }
}
